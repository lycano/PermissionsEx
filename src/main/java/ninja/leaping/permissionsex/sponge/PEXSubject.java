/**
 * PermissionsEx
 * Copyright (C) zml and PermissionsEx contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ninja.leaping.permissionsex.sponge;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import ninja.leaping.permissionsex.data.Caching;
import ninja.leaping.permissionsex.data.ImmutableOptionSubjectData;
import ninja.leaping.permissionsex.sponge.option.OptionSubjectData;
import org.spongepowered.api.service.permission.MemorySubjectData;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectCollection;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.context.Context;
import org.spongepowered.api.service.permission.context.ContextCalculator;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Permissions subject implementation
 */
public class PEXSubject implements Subject, Caching {
    private final PermissionsExPlugin plugin;
    private final SubjectData data, transientData;
    private final String identifier;

    public PEXSubject(String identifier, OptionSubjectData data, PermissionsExPlugin plugin) {
        this.plugin = plugin;
        this.data = data;
        this.identifier = identifier;
        this.transientData = new MemorySubjectData(plugin);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Optional<CommandSource> getCommandSource() {
        return getContainingCollection().getCommandSource(this.identifier);
    }

    @Override
    public PEXSubjectCollection getContainingCollection() {
        return null;
    }

    @Override
    public SubjectData getData() {
        return data;
    }

    @Override
    public SubjectData getTransientData() {
        return transientData;
    }

    @Override
    public boolean hasPermission(Set<Context> contexts, String permission) {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        System.out.println("Checking permission " + permission + " for " + identifier);
        return false;
    }

    @Override
    public Tristate getPermissionValue(Set<Context> contexts, String permission) {
        return null;
    }

    @Override
    public boolean isChildOf(Subject parent) {
        return false;
    }

    @Override
    public boolean isChildOf(Set<Context> contexts, Subject parent) {
        return false;
    }

    @Override
    public Set<Context> getActiveContexts() {
        Set<Context> set = new HashSet<>();
        for (ContextCalculator calc : plugin.getContextCalculators()) {
            calc.accumulateContexts(this, set);
        }
        return ImmutableSet.copyOf(set);
    }

    @Override
    public List<Subject> getParents() {
        return Collections.emptyList();
    }

    @Override
    public List<Subject> getParents(Set<Context> contexts) {
        return null;
    }


    @Override
    public void clearCache(ImmutableOptionSubjectData newData) {

    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PEXSubject)) {
            return false;
        }

        PEXSubject otherSubj = (PEXSubject) other;

        return this.identifier.equals(otherSubj.identifier)
                && this.data.equals(otherSubj.data);
    }
}
